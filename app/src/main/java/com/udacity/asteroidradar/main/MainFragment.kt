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
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import timber.log.Timber

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

        /*val adapter = SleepNightAdapter(AsteroidListener { asteroidId ->
            Toast.makeText(context, "${asteroidId}", Toast.LENGTH_LONG).show()
        })*/
        val adapter = AsteroidAdapter(AsteroidListener { asteroid ->
            //Toast.makeText(context, "${asteroidId}", Toast.LENGTH_LONG).show()
            AsteroidViewModel.onAsteroidClicked(asteroid)
        })
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

        AsteroidViewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let { this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(asteroid))
                AsteroidViewModel.onAsteroidNavigated()
            }
        })
        //asteroid1 is hardcoded above

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
