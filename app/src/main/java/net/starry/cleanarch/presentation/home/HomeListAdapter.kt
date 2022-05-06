package net.starry.cleanarch.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import net.starry.cleanarch.databinding.ItemUserInfoBinding
import net.starry.cleanarch.databinding.ItemUserRepoBinding
import net.starry.cleanarch.domain.model.GitRepo
import net.starry.cleanarch.domain.model.GitUser
import net.starry.cleanarch.presentation.model.MainListItem
import net.starry.cleanarch.presentation.utils.ordinal

class HomeListAdapter(
    private val itemList: List<MainListItem>,
    private val onClickUser: (GitUser) -> Unit,
    private val onClickRepo: (GitUser, GitRepo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MainListItem.Header.ordinal() -> HeaderViewHolder(ItemUserInfoBinding.inflate(layoutInflater))
            MainListItem.RepoItem.ordinal() -> RepoViewHolder(ItemUserRepoBinding.inflate(layoutInflater))
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(itemList[0] as MainListItem.Header)
            is RepoViewHolder -> holder.bind(itemList[position] as MainListItem.RepoItem)
        }
    }

    override fun getItemViewType(position: Int): Int = itemList[position].ordinal()

    override fun getItemCount() = itemList.size

    private inner class HeaderViewHolder(
        private val binding: ItemUserInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainListItem.Header) {
            with(binding) {
                profileImageView.load(item.user.profileImageUrl)
                userNameTextView.text = item.user.name
                root.setOnClickListener {
                    onClickUser.invoke(item.user)
                }
            }
        }
    }

    private inner class RepoViewHolder(
        private val binding: ItemUserRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainListItem.RepoItem) {
            with(binding) {
                repoNameTextView.text = item.repo.name
                descriptionTextView.text = item.repo.description
                countOfStarTextView.text = item.repo.starCount
                root.setOnClickListener {
                    val header = itemList[0] as MainListItem.Header
                    onClickRepo.invoke(header.user, item.repo)
                }
            }
        }
    }
}