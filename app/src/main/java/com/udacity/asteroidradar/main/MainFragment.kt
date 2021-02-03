package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.annotation.RestrictTo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.RawJson
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.*
import timber.log.Timber
import java.lang.reflect.Array.get

class MainFragment : Fragment() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //val binding = FragmentMainBinding.inflate(inflater)
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val asteroid1 = Asteroid(1L,"second","sec",1.6,1.7,
            1.8,1.9,true)

        /*dataSource.insert(asteroid1)
        Timber.i("Data inserted")
        val asterList = dataSource.getAllAsteroids()
        asterList.value?.forEach {"Timbuk2:" + Timber.i(it.codename) }*/
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val AsteroidViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = AsteroidViewModel
        binding.lifecycleOwner = this
        //binding.viewModel = viewModel
        setHasOptionsMenu(true)

        val adapter = AsteroidAdapter()
        binding.asteroidRecycler.adapter = adapter

        /*viewModel.nights.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.data = it
            }
        })*/

        AsteroidViewModel.asteroidTempList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.asteroidList = it as MutableList<Asteroid>
            }
        })

        Timber.plant(Timber.DebugTree())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
