/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Workaround for https://issuetracker.google.com/issues/294137077.
 * The fix is to apply the following patch: https://android.googlesource.com/platform/tools/base/+/d6e8d6119%5E%21/#F0
 */

@file:Suppress("unused", "MemberVisibilityCanBePrivate", "ConvertToStringTemplate", "SpellCheckingInspection")

package com.android.build.gradle.internal.dependency

import com.android.SdkConstants
import com.android.ide.common.process.CachedProcessOutputHandler
import com.android.ide.common.process.LoggedProcessOutputHandler
import com.android.ide.common.process.ProcessExecutor
import com.android.ide.common.process.ProcessInfoBuilder
import com.android.utils.ILogger
import com.google.common.base.Preconditions
import org.gradle.api.JavaVersion
import java.io.File

class JdkTools(
    val javaHome: File,
    val processExecutor: ProcessExecutor,
    val logger: ILogger,
) {
    val jrtFsLocation: File = javaHome.resolve("lib").resolve("jrt-fs.jar")

    val jlinkVersion: String by lazy {
        val jlinkExecutable = javaHome.resolve("bin").resolve("jlink".optionalExe())
        Preconditions.checkArgument(
            jlinkExecutable.exists(),
            "jlink executable %s does not exist.",
            jlinkExecutable,
        )

        val pib = ProcessInfoBuilder().apply {
            setExecutable(jlinkExecutable)
            addArgs("--version")
        }

        val processOutputHandler = CachedProcessOutputHandler()
        processExecutor.execute(
            pib.createProcess(),
            processOutputHandler,
        ).rethrowFailure().assertNormalExitValue()

        val processOutput = processOutputHandler.processOutput.standardOutputAsString.trim()

        // Try to extract only major version in order to reduce build cache misses - b/234820480.
        // If we fail, get the whole version.
        return@lazy try {
            JavaVersion.toVersion(processOutput).majorVersion
        } catch (t: Throwable) {
            processOutput
        }
    }

    fun compileModuleDescriptor(
        moduleInfoJava: File,
        systemModulesJar: File,
        outDir: File,
    ) {
        val classpathArgValue = systemModulesJar.absolutePath
        val javacExecutable = javaHome.resolve("bin").resolve("javac".optionalExe())

        Preconditions.checkArgument(
            javacExecutable.exists(),
            "javac executable %s does not exist.",
            javacExecutable,
        )

        val pib = ProcessInfoBuilder().apply {
            setExecutable(javacExecutable)
            addArgs("--system=none")
            addArgs("--patch-module=java.base=$classpathArgValue")
            addArgs("-d", outDir.absolutePath)
            addArgs(moduleInfoJava.absolutePath)
        }

        processExecutor.execute(
            pib.createProcess(),
            LoggedProcessOutputHandler(logger),
        ).rethrowFailure().assertNormalExitValue()
    }

    fun createJmodFromModularJar(
        jmodFile: File,
        jlinkVersion: String,
        moduleJar: File,
    ) {
        val jmodExecutable = javaHome.resolve("bin").resolve("jmod".optionalExe())
        Preconditions.checkArgument(
            jmodExecutable.exists(),
            "jmod executable %s does not exist.",
            jmodExecutable,
        )
        val pib = ProcessInfoBuilder().apply {
            setExecutable(jmodExecutable)
            addArgs("create")
            addArgs("--module-version", jlinkVersion)
            addArgs("--target-platform", "LINUX-OTHER")
            addArgs("--class-path", moduleJar.absolutePath)
            addArgs(jmodFile.absolutePath)
        }

        processExecutor.execute(
            pib.createProcess(),
            LoggedProcessOutputHandler(logger),
        ).rethrowFailure().assertNormalExitValue()
    }

    fun linkJmodsIntoJdkImage(jmodDir: File, moduleName: String, outDir: File) {
        val jlinkExecutable = javaHome.resolve("bin").resolve("jlink".optionalExe())
        Preconditions.checkArgument(
            jlinkExecutable.exists(),
            "jlink executable %s does not exist.",
            jlinkExecutable,
        )

        val pib = ProcessInfoBuilder().apply {
            setExecutable(jlinkExecutable)
            addArgs("--module-path", jmodDir.absolutePath)
            addArgs("--add-modules", moduleName)
            addArgs("--output", outDir.absolutePath)
            addArgs("--disable-plugin", "system-modules")
        }

        processExecutor.execute(
            pib.createProcess(),
            LoggedProcessOutputHandler(logger),
        ).rethrowFailure().assertNormalExitValue()
    }
}

internal fun String.optionalExe() =
    if (SdkConstants.CURRENT_PLATFORM == SdkConstants.PLATFORM_WINDOWS) this + ".exe" else this
