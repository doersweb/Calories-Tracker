package com.doersweb.onboarding_domain.di

import com.doersweb.onboarding_domain.usecases.ValidateNutrientPercentage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun providesValidateNutrientPect() : ValidateNutrientPercentage = ValidateNutrientPercentage()

}