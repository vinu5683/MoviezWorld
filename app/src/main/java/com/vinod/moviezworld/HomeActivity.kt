package com.vinod.moviezworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinod.moviezworld.databinding.ActivityMainBinding
import com.vinod.moviezworld.interfaces.MovieItemInteractionListener
import com.vinod.moviezworld.mvvm.viewmodels.MoviesHomeViewModel
import com.vinod.moviezworld.pagination.MoviesPagingAdapter
import com.vinod.moviezworld.pagination.PageLoaderAdapter
import com.vinod.moviezworld.viewcomponents.homecategory.CategoryAdapter
import com.vinod.moviezworld.viewcomponents.homecategory.CategoryItemClickListener
import com.vinod.moviezworld.viewcomponents.homecategory.HomeCategoryModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), CategoryItemClickListener, MovieItemInteractionListener {


    private lateinit var bindin: ActivityMainBinding

    lateinit var moviesPagerAdapter: MoviesPagingAdapter
    private val moviesPagerHomeViewModel: MoviesHomeViewModel by viewModels()

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var categories: ArrayList<HomeCategoryModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindin.root)
        setUpCategoriesRecyclerView()
        setUpMoviePagers()

    }

    private fun setUpCategoriesRecyclerView() {

        categories = ArrayList()

        //flexible category list
        categories = getCategoriesList(0)

        categoryAdapter = CategoryAdapter(categories, this)

        bindin.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bindin.rvCategories.adapter = categoryAdapter
    }

    private fun getCategoriesList(i: Int): java.util.ArrayList<HomeCategoryModel> {
        categories.apply {
            var c = 0
            add(HomeCategoryModel("Home", i == c++))
            add(HomeCategoryModel("Movie", i == c++))
            add(HomeCategoryModel("Series", i == c++))
            add(HomeCategoryModel("Episode", i == c++))
        }
        return categories
    }

    private fun setUpMoviePagers() {
        moviesPagerAdapter = MoviesPagingAdapter()
        bindin.rvLayout.layoutManager = GridLayoutManager(this, 3)
//        bindin.rvLayout.setHasFixedSize(true)
        bindin.rvLayout.adapter = moviesPagerAdapter.withLoadStateHeaderAndFooter(
            header = PageLoaderAdapter(),
            footer = PageLoaderAdapter()
        )
        moviesPagerHomeViewModel.getCategoryData(
            "all",
            "",
            "2022"
        )
        moviesPagerHomeViewModel.allMoviesList?.let { it ->
            it.observe(this) {
                moviesPagerAdapter.submitData(lifecycle, it)
            }
        }

    }

    override fun onCategoryItemClickLister(pos: Int) {
        categories.clear()
        categories = getCategoriesList(pos)

        setUpMoviePagers()

        moviesPagerHomeViewModel.getCategoryData(
            "all",
            if (pos == 0) "" else categories[pos].title,
            "2022"
        )
        moviesPagerHomeViewModel.allMoviesList?.let { it ->
            it.observe(this) {
                moviesPagerAdapter.submitData(lifecycle, it)
            }
        }

        categoryAdapter.notifyDataSetChanged()

    }

    override fun onMovieItemClickListener() {
    }

    override fun onMovieBookMarkClickListener() {


    }
}