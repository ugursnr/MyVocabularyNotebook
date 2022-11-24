package com.ugurrsnr.myvocabularynotebook.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ugurrsnr.myvocabularynotebook.databinding.RecyclerRowBinding
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

class VocabulariesHomeAdapter : RecyclerView.Adapter<VocabulariesHomeAdapter.VocabulariesHomeViewHolder>() {

    lateinit var onItemDeleteClicked : ((Vocabulary) -> Unit)
    lateinit var onItemClicked : ((Vocabulary) -> Unit)

    val allVocabulariesList = arrayListOf<Vocabulary>()
    class VocabulariesHomeViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabulariesHomeViewHolder {
        return VocabulariesHomeViewHolder(RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VocabulariesHomeViewHolder, position: Int) {
        val vocabulary = allVocabulariesList[position]
        holder.binding.apply {
            actualWordTV.text = vocabulary.vocabulary
            translationWordTV.text = vocabulary.vocabularyTranslation

            deleteButtonRV.setOnClickListener {
                onItemDeleteClicked.invoke(vocabulary)
                notifyItemRemoved(position)
            }

        }
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(allVocabulariesList[position])
        }

    }

    override fun getItemCount(): Int {
        return allVocabulariesList.size
    }

    fun updateVocabularyList(newList : List<Vocabulary>){
        allVocabulariesList.clear()
        allVocabulariesList.addAll(newList)
        notifyDataSetChanged()
    }


}