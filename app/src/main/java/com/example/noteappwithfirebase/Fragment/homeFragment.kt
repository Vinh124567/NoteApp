    package com.example.noteappwithfirebase.Fragment
    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.navigation.findNavController
    import androidx.navigation.fragment.navArgs
    import androidx.recyclerview.widget.StaggeredGridLayoutManager
    import com.example.noteappwithfirebase.MainActivity
    import com.example.noteappwithfirebase.Model.Note
    import com.example.noteappwithfirebase.Model.User
    import com.example.noteappwithfirebase.R
    import com.example.noteappwithfirebase.ViewModel.NoteViewModel
    import com.example.noteappwithfirebase.adapter.NoteAdapter
    import com.example.noteappwithfirebase.databinding.FragmentHomeBinding

    class homeFragment : Fragment(R.layout.fragment_home) {
        private var homeBinding: FragmentHomeBinding? = null
        private val binding get() = homeBinding!!
        private lateinit var uid: String
        private lateinit var notesViewModel : NoteViewModel
        private lateinit var noteAdapter: NoteAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            notesViewModel = (activity as MainActivity).noteViewModel
            uid= notesViewModel.getCurrentUserUid().toString()
            setUpRecyclerView()
            Toast.makeText(requireContext(), uid, Toast.LENGTH_SHORT).show()
            binding.addNoteFab.setOnClickListener{
                it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
            }

        }


        private fun setUpRecyclerView(){
            noteAdapter=NoteAdapter(uid)
            binding.homeRecyclerView.apply {
                layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
                adapter=noteAdapter
            }
            activity?.let {
                notesViewModel.loadNote(uid)
                notesViewModel.allUser.observe(viewLifecycleOwner){note->
                    noteAdapter.differ.submitList(note)
                }

            }
        }


    }