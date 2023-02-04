package com.example.aston_intensive_course_work.presentation.fragments.details_fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.example.aston_intensive_course_work.domain.models.items_models.EpisodeItemDomain
import com.example.aston_intensive_course_work.domain.use_cases.GetAllEpisodesByIdsUseCase
import com.example.aston_intensive_course_work.getOrAwaitValue
import com.example.aston_intensive_course_work.presentation.fragments.list_fragments.MockListCallback
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
class CharacterDetailsViewModelTest {

    private val getAllEpisodesByIdsUseCase = mock<GetAllEpisodesByIdsUseCase>()

    private val testPageData: PagingData<EpisodeItemDomain> = PagingData.from(
        TEST_EPISODE_DOMAIN_LIST
    )
    private val testEmptyPageData: PagingData<EpisodeItemDomain> = PagingData.from(
        emptyList()
    )

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

    @Test(expected = UninitializedPropertyAccessException::class)
    fun characterDetailsViewModel_Initialization() {
        val characterDetailsViewModel =
            CharacterDetailsViewModel(getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase)

        characterDetailsViewModel.getSelectedItem()
    }

    @Test
    fun characterDetailsViewModel_onUserSelectItem_ItemUpdateCorrectly() = runTest {
        Mockito.`when`(getAllEpisodesByIdsUseCase.execute(TEST_LIST))
            .thenReturn(flowOf(testEmptyPageData))

        val characterDetailsViewModel =
            CharacterDetailsViewModel(getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase)

        characterDetailsViewModel.onUserSelectItem(TEST_CHARACTER)

        val actualSelectedItem = characterDetailsViewModel.getSelectedItem()

        Assert.assertEquals(TEST_CHARACTER, actualSelectedItem)
    }

    @Test
    fun characterDetailsViewModel_onUserSelectItem_AttachedSubItemsUpdateCorrectly() =
        runTest {
            Mockito.`when`(getAllEpisodesByIdsUseCase.execute(TEST_LIST))
                .thenReturn(flowOf(testPageData))

            val characterDetailsViewModel =
                CharacterDetailsViewModel(getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase)

            characterDetailsViewModel.onUserSelectItem(TEST_CHARACTER)

            val differ = AsyncPagingDataDiffer(
                diffCallback = MockEpisodeItemDiffCallback(),
                updateCallback = MockListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            val data = characterDetailsViewModel.attachedItemsList.getOrAwaitValue()
            differ.submitData(data)
            val actualData = differ.snapshot().items

            Assert.assertEquals(TEST_EPISODE_LIST, actualData)
        }

    @Test
    fun characterDetailsViewModel_onErrorOccur_OccurErrorIsNotUnknownHostException() =
        runTest {
            Mockito.`when`(getAllEpisodesByIdsUseCase.execute(TEST_LIST))
                .thenReturn(flowOf(testEmptyPageData), flowOf(testPageData))

            val characterDetailsViewModel =
                CharacterDetailsViewModel(getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase)

            characterDetailsViewModel.onUserSelectItem(TEST_CHARACTER)

            val loadAfterError = launch {
                characterDetailsViewModel.onErrorOccur(TEST_ERROR)
            }

            val differ = AsyncPagingDataDiffer(
                diffCallback = MockEpisodeItemDiffCallback(),
                updateCallback = MockListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            loadAfterError.join()

            launch {
                val data = characterDetailsViewModel.attachedItemsList.getOrAwaitValue()
                differ.submitData(data)
                val actualData = differ.snapshot().items

                Assert.assertEquals(EMPTY_LIST, actualData)
            }
        }

    @Test
    fun characterDetailsViewModel_onErrorOccur_OccurErrorIsUnknownHostException() =
        runTest {
            Mockito.`when`(getAllEpisodesByIdsUseCase.execute(TEST_LIST))
                .thenReturn(flowOf(testEmptyPageData), flowOf(testPageData))

            val characterDetailsViewModel =
                CharacterDetailsViewModel(getAllEpisodesByIdsUseCase = getAllEpisodesByIdsUseCase)

            characterDetailsViewModel.onUserSelectItem(TEST_CHARACTER)

            val differ = AsyncPagingDataDiffer(
                diffCallback = MockEpisodeItemDiffCallback(),
                updateCallback = MockListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            val loadAfterError = launch {
                characterDetailsViewModel.onErrorOccur(TEST_UNKNOWN_HOST_EXCEPTION)
            }

            loadAfterError.join()

            launch {
                val data = characterDetailsViewModel.attachedItemsList.getOrAwaitValue()
                differ.submitData(data)
                val actualData = differ.snapshot().items

                Assert.assertEquals(TEST_EPISODE_LIST, actualData)
            }
        }
}