package com.example.noteappwithfirebase.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.noteappwithfirebase.MainActivity
import com.example.noteappwithfirebase.Model.User
import com.example.noteappwithfirebase.R
import com.example.noteappwithfirebase.ViewModel.NoteViewModel
import com.example.noteappwithfirebase.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var RegistBinding: FragmentRegistrationBinding? = null
    private val binding get() = RegistBinding!!
    private lateinit var notesViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        RegistBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        binding.buttonRegister.setOnClickListener {
            var email=binding.editTextEmaill.text.toString()
            var password=binding.editTextPasswordd.text.toString()
            notesViewModel.registerWithEmail(email,password){isRegis,uid ->
                if (isRegis) {
                    uid?.let {
                        Toast.makeText(requireActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        view.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
                } else {
                    Toast.makeText(requireActivity(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }



}