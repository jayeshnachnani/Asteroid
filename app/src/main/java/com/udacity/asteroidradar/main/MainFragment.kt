package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidApiFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response
    //val application = requireNotNull(this.activity).application
    //val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
        //val application = requireNotNull(this.activity).application
        //val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val asteroid1 = Asteroid(1L,"second","sec",1.6,1.7,
            1.8,1.9,true)
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val AsteroidViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = AsteroidViewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)


        val adapter = AsteroidAdapter(AsteroidListener { asteroid ->
            AsteroidViewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        AsteroidViewModel.asteroidTempList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.asteroidList = it as MutableList<Asteroid>
            }
        })

        AsteroidViewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let { this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(asteroid))
                AsteroidViewModel.onAsteroidNavigated()
            }
        })

        Timber.plant(Timber.DebugTree())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val AsteroidViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)
        AsteroidViewModel.updateFilter(
            when (item.itemId) {
                R.id.show_week_menu -> AsteroidApiFilter.VIEW_WEEK
                R.id.show_today_menu -> AsteroidApiFilter.VIEW_TODAY
                else -> AsteroidApiFilter.VIEW_SAVED
            }
        )
        return true
    }
}
