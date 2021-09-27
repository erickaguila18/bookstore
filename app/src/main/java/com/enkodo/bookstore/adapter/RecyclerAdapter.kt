package com.enkodo.bookstore.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enkodo.bookstore.R
import com.enkodo.bookstore.api.response.books.BookResponse


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder>() {
    lateinit var tasks: BookResponse
    lateinit var mItemClickListener: MyAdapterListener
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TaskViewHolder {
        return TaskViewHolder(parent)
    }

    fun setData(tasks: BookResponse) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(tasks, position)
    }

    override fun getItemCount(): Int = tasks.results.books.size

    inner class TaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_books, parent, false)
    ) {

        fun bind(task: BookResponse, position: Int): Unit = with(itemView) {
            val tvListItemAuthor = itemView.findViewById<TextView>(R.id.tvListItemAuthor)
            val tvListItemTitle = itemView.findViewById<TextView>(R.id.tvListItemTitle)
            val tvListItemDateTime = itemView.findViewById<TextView>(R.id.tvListItemDateTime)
            val ivListItem = itemView.findViewById<ImageView>(R.id.ivListItem)
            val tvListItemType = itemView.findViewById<TextView>(R.id.tvListItemType)

            tvListItemAuthor.text = task.results.books[position].author
            tvListItemTitle.text = task.results.books[position].title
            tvListItemType.text = task.results.books[position].description
            itemView.setOnClickListener {
                task.results.books[position].img.let { mUrl ->
                    mItemClickListener.onItemViewClick(
                        mUrl,
                        position
                    )
                }
            }
            Glide.with(itemView.context)
                .load(task.results.books[position].img)
                .into(ivListItem)

            tvListItemDateTime.text = task.results.books[position].genre
        }
    }

    fun setOnItemClick(itemClickListener: MyAdapterListener): Unit {
        this.mItemClickListener = itemClickListener
    }

    interface MyAdapterListener {
        fun onItemViewClick(webUrl: String, position: Int)
    }
}