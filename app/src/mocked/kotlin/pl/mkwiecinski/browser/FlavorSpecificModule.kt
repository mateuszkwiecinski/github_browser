package pl.mkwiecinski.browser

import dagger.Module
import pl.mkwiecinski.mocked.di.MockedModule

@Module(includes = [MockedModule::class])
internal abstract class FlavorSpecificModule
