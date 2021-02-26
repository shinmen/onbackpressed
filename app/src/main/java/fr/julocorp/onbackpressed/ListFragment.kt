package fr.julocorp.onbackpressed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var repo: StuffRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        activity?.let { activity ->
            repo = DataStoreStuffRepository(activity)

            lifecycleScope.launch(Dispatchers.Main) {
                repo.getBunchOfStuff().collect { bunchOfStuff ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = StuffRecyclerViewAdapter(bunchOfStuff)
                    }
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}