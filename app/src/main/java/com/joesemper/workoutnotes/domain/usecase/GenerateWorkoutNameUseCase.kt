package com.joesemper.workoutnotes.domain.usecase

import com.joesemper.workoutnotes.data.datasource.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

private const val FIRST_LETTER_CODE = 65
private const val LAST_LETTER_CODE = 90

class GenerateWorkoutNameUseCase @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: WorkoutRepository
) {

    suspend operator fun invoke(): String = withContext(defaultDispatcher) {

        val regex = Regex("[A-Z]")

        val names = repository.getAllWorkoutNames()
        val filteredNames = names.filter { it.matches(regex) }.sortedBy { it.first().code }

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)

        var result = FIRST_LETTER_CODE.toChar().toString()

        for (i in (FIRST_LETTER_CODE..LAST_LETTER_CODE)) {
            if (!filteredNames.contains(i.toChar().toString())) {
                result = i.toChar().toString()
                break
            }
            result = formatter.format(date)
        }

        return@withContext result

    }
}