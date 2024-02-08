import {Posts } from "../../components/post/Posts.tsx";
import styles from './style.module.scss';
import {useEffect, useState} from "react";
import {usePostStore} from "../../store/post-store.ts";
import {IPostDetail} from "../../store/interface.ts";

const HomePage = () => {
    const [posts, setPosts] = useState<IPostDetail[]>([]);
    const { fetchPublishedPosts } = usePostStore();

    useEffect(() => {
        getPublishedPosts();
    }, []);

    const getPublishedPosts = async () => {
        try {
            const publishedPosts = await fetchPublishedPosts();
            setPosts(publishedPosts);
        } catch (error) {
            console.error('Failed to fetch published posts:', error);
        }
    };

    return (
        <div className={styles.homepage_container}>

            <div className={styles.content}>
                {/*<NewPost onAddPost={handleNewPost} />*/}
                <Posts posts={posts} />
            </div>

        </div>
    );
};

export default HomePage;
