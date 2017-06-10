package org.blendin.blendin.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.blendin.blendin.R;
import org.blendin.blendin.dagger.AppComponent;
import org.blendin.blendin.dagger.DaggerAppComponent;
import org.blendin.blendin.feed.dummy.DummyPosts;
import org.blendin.blendin.models.Post;

public class PostsFragment extends Fragment implements PostsView {

    private PostsPresenter presenter;

    private static int COLUMN_NUMBER = 1;

    private OnListFragmentInteractionListener mListener;

    public PostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = DaggerAppComponent.builder().build();
        appComponent.inject(this);
        presenter = new PostsPresenter(this, appComponent.postsRepository());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.createPost();
        presenter.fetchPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (COLUMN_NUMBER <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, COLUMN_NUMBER));
            }
            recyclerView.setAdapter(new PostsAdapter(DummyPosts.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Post item);
    }
}