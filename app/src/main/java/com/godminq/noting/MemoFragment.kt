package com.godminq.noting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.godminq.noting.databinding.FragmentMemoBinding

// editTextTextPersonName 클릭시 minline = 3 되도록 하기

class MemoFragment : Fragment() {

    lateinit var binding: FragmentMemoBinding

    var helper: RoomHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        super.onViewCreated(view, savedInstanceState)

        helper = Room.databaseBuilder(requireContext(), RoomHelper::class.java, "room_memo")
            .addMigrations(MigrateDatabase.MIGRATE_1_2)
            .allowMainThreadQueries()
            .build()

        val adapter = RecyclerAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())
        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonSave.setOnClickListener {
            if (binding.editMemo.text.toString().isNotEmpty()) {
                val memo = RoomMemo(binding.editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: listOf())

                adapter.notifyDataSetChanged()
                binding.editMemo.setText("")
            }
        }

    }
}
object MigrateDatabase {
    val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val alter = "ALTER table room_memo add column new_title text"
            database.execSQL(alter)
        }
    }
}

