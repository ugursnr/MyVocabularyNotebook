package com.ugurrsnr.myvocabularynotebook.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ugurrsnr.myvocabularynotebook.databinding.RecyclerRowBinding
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

class VocabulariesHomeAdapter : RecyclerView.Adapter<VocabulariesHomeAdapter.VocabulariesHomeViewHolder>() {

    private val difUtil = object : DiffUtil.ItemCallback<Vocabulary>(){
        override fun areItemsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
            return oldItem.vocabularyID == newItem.vocabularyID
        }

        override fun areContentsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,difUtil)

    class VocabulariesHomeViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabulariesHomeViewHolder {
        return VocabulariesHomeViewHolder(RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VocabulariesHomeViewHolder, position: Int) {
        val vocabulary = differ.currentList[position]

        holder.binding.apply {
            actualWordTV.text = vocabulary.vocabulary
            translationWordTV.text = vocabulary.vocabularyTranslation
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}