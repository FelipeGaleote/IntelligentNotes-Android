package com.felipeg.intelligentnotes.annotation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.felipeg.intelligentnotes.databinding.FragmentMyAnnotationsBinding


class MyAnnotationsFragment : Fragment() {

    private var _binding: FragmentMyAnnotationsBinding? = null
    private lateinit var myAnnotationsViewModel: MyAnnotationsViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myAnnotationsViewModel =
            ViewModelProvider(requireActivity()).get(MyAnnotationsViewModel::class.java)

        _binding = FragmentMyAnnotationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAnnotationsViewModel.myNotesResult.observe(requireActivity()) { annotations ->
            _binding?.notesList?.adapter = MyAnnotationsAdapter(annotations)
            _binding?.notesList?.layoutManager = LinearLayoutManager(requireContext())
        }
        myAnnotationsViewModel.listMyNotes()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}