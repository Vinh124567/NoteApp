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
import com.example.noteappwithfirebase.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var LoginBinding: FragmentLoginBinding? = null
    private val binding get() = LoginBinding!!
    private lateinit var notesViewModel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        binding.textRegister.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        notesViewModel.loadAccount()
        notesViewModel.loadAccount()?.let { user ->
            binding.editTextEmail.setText(user.email)
            binding.editTextPassword.setText(user.password)
            binding.checkBoxRememberPassword.isChecked = true
        }

        binding.buttonLogin.setOnClickListener { view ->
            val userInput = binding.editTextEmail.text.toString()
            val userPassword = binding.editTextPassword.text.toString()
            notesViewModel.loginWithEmail(userInput,userPassword){ isLogin->
                if(isLogin){
                    Toast.makeText(requireActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }else{
                    Toast.makeText(requireActivity(), "tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}