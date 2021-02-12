package com.example.kopa.fragments.adapters

import android.R.attr.radius
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kopa.R
import com.example.kopa.databinding.DeclarationLayoutBinding
import com.example.kopa.model.Declaration
import com.facebook.FacebookSdk.getApplicationContext
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


class DeclarationsAdapter(val clickListener: DeclarationListener,type:String):
        ListAdapter<Declaration, DeclarationsAdapter.ViewHolder>(DeadlinesDiffCallback()) {
    val typeAdapter = type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            else -> {
                val item = getItem(position)
                holder.bind(item, clickListener,typeAdapter)
            }
        }
        val item = getItem(position)
        holder.bind(item, clickListener,typeAdapter)
    }
    class ViewHolder private constructor(val binding: DeclarationLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = DeclarationLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
        fun bind(item: Declaration, clickListener: DeclarationListener,typeAdapter:String) {
            binding.declaration = item
            if(item.liked && typeAdapter == "like"){
                binding.likeImageView.setImageResource(R.drawable.ic_heart_red)

            }
            else if(item.liked && typeAdapter == "like"){
                binding.likeImageView.setImageResource(R.drawable.ic_heart_white)
            }else{
                binding.likeImageView.visibility = View.INVISIBLE
            }
            binding.textSizeCountry.text = item.sizeRegion
            binding.price.text = item.price
            binding.textModel.text = item.model
            binding.textMatheralMaterial.text = item.material
            binding.textSizeSize.text = item.size
            binding.textSizeLenght.text = item.sizeLength
            binding.textSizeWeight.text = item.sizeWidth
            binding.clickListener = clickListener
            binding.executePendingBindings()
            Picasso.get()
                    .load(item.photoArray[0])
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(binding.imageViewPhoto)
        }
    }
}
class DeadlinesDiffCallback: DiffUtil.ItemCallback<Declaration>(){
    override fun areItemsTheSame(oldItem: Declaration, newItem: Declaration): Boolean {
        return oldItem.id== newItem.id
    }

    override fun areContentsTheSame(oldItem: Declaration, newItem: Declaration): Boolean {
        return oldItem == newItem
    }

}

class DeclarationListener(val clickListener: (declarationName: String) -> Unit) {
    fun onClick(declaration: Declaration) = clickListener(declaration.id)
}