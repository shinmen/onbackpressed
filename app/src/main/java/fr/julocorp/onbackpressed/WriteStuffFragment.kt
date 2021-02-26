package fr.julocorp.onbackpressed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteStuffFragment : Fragment() {

    private lateinit var repo: StuffRepository

    private val saveCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                lifecycleScope.launch(Dispatchers.Main) {
                    val stuff = view?.findViewById<EditText>(R.id.textview_first)?.text
                    repo.saveStuff(stuff.toString())
                    Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        }

    private val invalidCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.findViewById<EditText>(R.id.textview_first)?.let {
                    it.error = getString(R.string.error)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write_stuff, container, false)

        activity?.let { activity ->
            repo = DataStoreStuffRepository(activity)

            view.findViewById<EditText>(R.id.textview_first)
                .addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        saveCallback.remove()
                        invalidCallback.remove()
                        val callback = if (!s.isNullOrBlank()) {
                            saveCallback
                        } else {
                            invalidCallback
                        }

                        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
                    }
                })


            activity.onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                invalidCallback
            )
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = WriteStuffFragment()
    }
}