package dk.chen.garbagev1.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.chen.garbagev1.domain.ItemRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingsModule {

    @Singleton
    @Binds
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository
}