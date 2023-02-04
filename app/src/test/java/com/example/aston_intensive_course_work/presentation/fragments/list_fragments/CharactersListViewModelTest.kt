package com.example.aston_intensive_course_work.presentation.fragments.list_fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.models.items_models.CharacterItemDomain
import com.example.aston_intensive_course_work.domain.use_cases.GetCharactersUseCase
import com.example.aston_intensive_course_work.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
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
class CharactersListViewModelTest {

    private val getCharactersUseCase = mock<GetCharactersUseCase>()

    private val emptyPagingData: PagingData<CharacterItemDomain> = PagingData.from(emptyList())
    private val testPagingData: PagingData<CharacterItemDomain> =
        PagingData.from(TESTED_CHARACTERS_DOMAIN_LIST)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun charactersListViewModel_Initialization() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN

        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(emptyPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        val actualFiltersSet = charactersListViewModel.filtersSet

        Assert.assertTrue(charactersListViewModel.getCurrentQuery().isEmpty())
        Assert.assertEquals(EMPTY_FILTERS, actualFiltersSet)
    }

    @Test
    fun charactersListViewModel_correctLoadInitialData() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN
        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(testPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        val differ = AsyncPagingDataDiffer(
            diffCallback = MockDiffCallback(),
            updateCallback = MockListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val data = charactersListViewModel.itemsList.getOrAwaitValue()
        differ.submitData(pagingData = data)
        val actualData = differ.snapshot().items

        Assert.assertEquals(TESTED_CHARACTERS_LIST, actualData)
    }

    @Test
    fun charactersListViewModel_onUserSelectFilters_FiltersUpdateCorrectly() = runTest {
        val testFiltersSet = TEST_FILTERS_DOMAIN

        Mockito.`when`(getCharactersUseCase.execute(testFiltersSet))
            .thenReturn(flowOf(emptyPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        charactersListViewModel.onUserFiltersApply(filters = TEST_FILTERS)

        val actualFiltersSet = charactersListViewModel.filtersSet

        Assert.assertEquals(TEST_FILTERS, actualFiltersSet)
    }

    @Test
    fun charactersListViewModel_onUserQueryInput_QueryUpdateCorrectly() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN

        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(emptyPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        val fetchWithFilter = launch {
            charactersListViewModel.onUserQueryInput(query = TEST_QUERY)
        }

        fetchWithFilter.join()
        val actualQuery = charactersListViewModel.getCurrentQuery()

        Assert.assertEquals(TEST_QUERY, actualQuery)
    }

    @Test
    fun charactersListViewModel_onUserQueryInput_FilterDataCorrectly() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN
        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(testPagingData))

        val differ = AsyncPagingDataDiffer(
            diffCallback = MockDiffCallback(),
            updateCallback = MockListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        charactersListViewModel.onUserQueryInput(TEST_QUERY)

        val data = charactersListViewModel.itemsList.getOrAwaitValue()
        differ.submitData(pagingData = data)
        val actualData = differ.snapshot().items

        Assert.assertEquals(TESTED_CHARACTERS_FILTERED_LIST, actualData)
    }

    @Test
    fun charactersListViewModel_onErrorOccur_OccurErrorIsNotUnknownHostException() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN
        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(emptyPagingData)).thenReturn(flowOf(testPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        val loadAfterError = launch {
            charactersListViewModel.onErrorOccur(TEST_ERROR)
        }

        val differ = AsyncPagingDataDiffer(
            diffCallback = MockDiffCallback(),
            updateCallback = MockListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        loadAfterError.join()

        launch {
            val data = charactersListViewModel.itemsList.getOrAwaitValue()
            differ.submitData(pagingData = data)
            val actualData = differ.snapshot().items

            Assert.assertEquals(EMPTY_LIST, actualData)
        }
    }

    @Test
    fun charactersListViewModel_onErrorOccur_OccurErrorIsUnknownHostException() = runTest {
        val emptyFiltersSet = EMPTY_FILTERS_DOMAIN
        Mockito.`when`(getCharactersUseCase.execute(emptyFiltersSet))
            .thenReturn(flowOf(emptyPagingData), flowOf(testPagingData))

        val charactersListViewModel =
            CharactersListViewModel(getCharactersUseCase = getCharactersUseCase)

        val differ = AsyncPagingDataDiffer(
            diffCallback = MockDiffCallback(),
            updateCallback = MockListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val loadAfterError = launch {
            charactersListViewModel.onErrorOccur(TEST_UNKNOWN_HOST_EXCEPTION)
        }

        loadAfterError.join()

        launch {
            val data = charactersListViewModel.itemsList.getOrAwaitValue()
            differ.submitData(pagingData = data)
            val actualData = differ.snapshot().items

            Assert.assertEquals(TESTED_CHARACTERS_LIST, actualData)
        }
    }
}
