package com.pycreations.bookvilla.data.utils

import android.content.Context
import android.content.Intent

object Constants {
    fun shareBook(context: Context,text : String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,text)
        context.startActivity(Intent.createChooser(intent,"Choose one"))
    }

    val popularGenres1 = listOf(
        "Fiction",
        "Romance",
        "Fantasy",
        "Science Fiction",
        "Mystery",
        "Thriller",
        "Horror",
        "Historical Fiction",
        "Biography",
        "Self-Help",
    )

    val popularGenres2 = listOf(
        "Science",
        "Graphic Novels",
        "Business",
        "Health",
        "Religion",
        "Children",
        "Young Adult",
        "Nonfiction",
        "Psychology",
        "Philosophy",
    )


    val fictionGenres = listOf(
        "Fiction",
        "Fantasy",
        "Science Fiction",
        "Romance",
        "Mystery",
        "Thriller",
        "Horror",
        "Adventure",
        "Drama",
        "Historical Fiction",
        "Graphic Novels",
        "Short Stories",
        "Western",
        "Detective",
        "Crime",
        "Urban Fiction",
        "Fairy Tales"
    )

    val nonFictionGenres = listOf(
        "Nonfiction",
        "Biography",
        "Autobiography",
        "Memoir",
        "Self-Help",
        "Psychology",
        "Philosophy",
        "Religion",
        "Spirituality",
        "True Crime",
        "Politics",
        "Economics",
        "Sociology",
        "Social Science",
        "Anthropology"
    )

    val academicGenres = listOf(
        "Science",
        "Mathematics",
        "Physics",
        "Chemistry",
        "Biology",
        "Engineering",
        "Computer Science",
        "Medicine",
        "Education",
        "Law",
        "History",
        "Geography",
        "Political Science"
    )

    val childrenAndYA = listOf(
        "Children",
        "Juvenile Fiction",
        "Juvenile Nonfiction",
        "Young Adult",
        "Teen",
        "Picture Books",
        "Middle Grade",
        "Early Readers"
    )

    val businessAndTech = listOf(
        "Business",
        "Finance",
        "Management",
        "Marketing",
        "Entrepreneurship",
        "Technology",
        "Information Technology",
        "Artificial Intelligence",
        "Data Science",
        "Programming"
    )

    val artLifestyleGenres = listOf(
        "Art",
        "Photography",
        "Music",
        "Performing Arts",
        "Design",
        "Fashion",
        "Cooking",
        "Crafts",
        "Gardening",
        "Home Improvement",
        "Interior Design"
    )

    val wellnessGenres = listOf(
        "Health",
        "Mental Health",
        "Wellness",
        "Nutrition",
        "Fitness",
        "Meditation",
        "Yoga",
        "Diet"
    )

    val cultureAndLanguage = listOf(
        "Travel",
        "Culture",
        "Foreign Language",
        "English",
        "Linguistics",
        "Language Arts"
    )

    val hobbiesAndLeisure = listOf(
        "Games",
        "Puzzles",
        "Comics",
        "Humor",
        "Outdoors",
        "Sports",
        "Pets"
    )

    val myList: Map<String, List<String>> = mapOf(
        "Fiction" to fictionGenres,
        "Non-Fiction" to nonFictionGenres,
        "Academic" to academicGenres,
        "Children" to childrenAndYA,
        "Business" to businessAndTech,
        "Art Life Style" to artLifestyleGenres,
        "Wellness" to wellnessGenres,
        "Culture and Language" to cultureAndLanguage,
        "Hobbies and Leisure" to hobbiesAndLeisure
    )
}