package com.example.aston_intensive_course_work.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aston_intensive_course_work.domain.use_cases.GetLocationByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testScope = TestScope()
    private val testDispatcher = UnconfinedTestDispatcher(testScope.testScheduler)

    private val getLocationByIdUseCase = mock<GetLocationByIdUseCase>()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun mainViewModelTest_Initialization(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        val actualGenderFilter = mainViewModel.liveDataGenderFilters.value
        val actualStatusFilter = mainViewModel.liveDataStatusFilters.value
        val actualSelectedLocation = mainViewModel.selectedLocation.value

        Assert.assertNull(mainViewModel.selectedCharacter)
        Assert.assertNull(mainViewModel.selectedEpisode)
        Assert.assertNull(actualSelectedLocation)

        Assert.assertNotNull(actualGenderFilter)
        Assert.assertNotNull(actualStatusFilter)

        Assert.assertTrue(actualGenderFilter!!.isEmpty())
        Assert.assertTrue(actualStatusFilter!!.isEmpty())
    }

    @Test
    fun mainViewModelTest_OnUserSelectCharacter_CharacterItemUpdateCorrectly(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectCharacter(TEST_CHARACTER)

        Assert.assertEquals(TEST_CHARACTER, mainViewModel.selectedCharacter)
    }

    @Test
    fun mainViewModelTest_OnUserSelectEpisode_EpisodeItemUpdateCorrectly(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectEpisode(TEST_EPISODE)

        Assert.assertEquals(TEST_EPISODE, mainViewModel.selectedEpisode)
    }

    @Test
    fun mainViewModelTest_OnUserSelectLocation_LocationItemUpdateCorrectly(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectLocation(TEST_LOCATION)

        val actualLocation = mainViewModel.selectedLocation.value

        Assert.assertEquals(TEST_LOCATION, actualLocation)
    }

    @Test
    fun mainViewModelTest_OnUserSetGenderFilter_GenderFilterUpdateCorrectly(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectGenderFilter(TEST_GENDER_FILTER)

        val actualGenderFilter = mainViewModel.liveDataGenderFilters.value

        Assert.assertEquals(TEST_GENDER_FILTER, actualGenderFilter)
    }

    @Test
    fun mainViewModelTest_OnUserSetStatusFilter_StatusFilterUpdateCorrectly(){
        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectStatusFilter(TEST_STATUS_FILTER)

        val actualStatusFilter = mainViewModel.liveDataStatusFilters.value

        Assert.assertEquals(TEST_STATUS_FILTER, actualStatusFilter)
    }

    @Test
    fun mainViewModelTest_OnUserLoadLocation_LoadedLocationUpdateCorrectly() = testScope.runTest {
        Mockito.`when`(getLocationByIdUseCase.execute(TEST_LOCATION_URL)).thenReturn(
            TEST_LOCATION_DOMAIN)

        val mainViewModel = MainViewModel(getLocationByIdsUseCase = getLocationByIdUseCase)

        mainViewModel.onUserSelectLocationOnCharacterCard(TEST_LOCATION_URL)

        val actualLocation = mainViewModel.selectedLocation.value

        Assert.assertEquals(TEST_LOCATION, actualLocation)
    }
}