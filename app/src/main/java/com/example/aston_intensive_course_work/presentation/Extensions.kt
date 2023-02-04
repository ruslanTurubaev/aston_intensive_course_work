package com.example.aston_intensive_course_work.presentation

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.aston_intensive_course_work.presentation.models.items_models.CharacterItem
import com.example.aston_intensive_course_work.presentation.models.items_models.EpisodeItem
import com.example.aston_intensive_course_work.presentation.models.items_models.LocationItem

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> Activity.find(idRes: Int) = unsafeLazy<T> { findViewById(idRes) }

fun Context.getHalfWindowHeight(): Float {
    return (this.resources.displayMetrics.widthPixels / 2).toFloat()
}

fun characterItemFilter(character: CharacterItem, query: String): Boolean =
    (character.name.lowercase().contains(query.lowercase())
            || character.species.lowercase().contains(query.lowercase())
            || character.gender.lowercase().contains(query.lowercase())
            || character.status.lowercase().contains(query.lowercase()))

fun episodeItemFilter(episode: EpisodeItem, query: String): Boolean =
    (episode.name.lowercase().contains(query.lowercase())
            || episode.episode.lowercase().contains(query.lowercase())
            || episode.airDate.lowercase().contains(query.lowercase()))

fun locationItemFilter(location: LocationItem, query: String): Boolean =
    (location.name.lowercase().contains(query.lowercase())
            || location.type.lowercase().contains(query.lowercase())
            || location.dimension.lowercase().contains(query.lowercase()))