package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateTodoBinding
import com.example.todoapp.databinding.FragmentEditTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {
    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.titleTodo.text = "Edit Todo"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        binding.btnSubmit.setOnClickListener {
            val radioID = binding.radioGroupPriority.checkedRadioButtonId
            val radio = view.findViewById<RadioButton>(radioID)
            val priority = radio.tag.toString().toInt()

            viewModel.update(binding.txtTitle.text.toString(), binding.txtNotes.text.toString(), priority, uuid)

            Navigation.findNavController(it).popBackStack()
        }
        binding.radioListener = this
        binding.saveListener = this
        observeViewModel()


//        binding.btnSubmit.setOnClickListener {
//
//            val radioId = binding.radioGroupPriority.checkedRadioButtonId
//            val radio = it.findViewById<RadioButton>(radioId)
//            val priority = radio.tag.toString().toInt()
//
//            viewModel.update(binding.txtTitle.text.toString(), binding.txtNotes.text.toString(), priority, uuid)
//        }
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

//    override fun onTodoSaveChangesClick(v: View, obj: Todo) {
//        viewModel.updateTodo(binding.todo!!)
//        Toast.makeText(v.context, "Todo Updated", Toast.LENGTH_SHORT).show()
//    }

    fun observeViewModel(){
        viewModel.todoLd.observe(viewLifecycleOwner, Observer {
            binding.todo = it
//            binding.txtTitle.setText(it.title)
//            binding.txtNotes.setText(it.notes)
//
//            when(it.priority) {
//                1-> binding.radioLow.isChecked = true
//                2-> binding.radioMedium.isChecked = true
//                else-> binding.radioHigh.isChecked = true
//            }
        }
        )
    }

    override fun onTodoEditClick(v: View) {
        viewModel.updateTodo(binding.todo!!)
    }

}