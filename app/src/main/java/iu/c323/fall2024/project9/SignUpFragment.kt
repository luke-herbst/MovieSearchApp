package iu.c323.fall2024.project9

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import iu.c323.fall2024.project9.databinding.FragmentSignUpBinding

private const val TAG = "LoginFragment"
class SignUpFragment: Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener{
            val email = binding.etEmailSignUp.text.toString()
            val password = binding.etPasswordSignUp.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigate_to_login)
                } else {
                    Log.e(TAG, "createUserWithEmail failed", task.exception)
                    Toast.makeText(this.context, "Sign-up failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
