import {Posts } from "../../components/post/Posts.tsx";
import styles from './style.module.scss';
import {useEffect, useState} from "react";
import {usePostStore} from "../../store/post-store.ts";
import {IPostDetail} from "../../store/interface.ts";
import PostOverviewCardWithName from "../../components/post-overview-card-withname/index.tsx";

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
            <div className={styles.homepage}>
                <div className={styles.homepage_content}>
                    <div className={styles.content}>
                        <h1 style={{textAlign: 'center'}}>All the Posts</h1>
                        {
                            posts.map((post: IPostDetail) => {
                                return (
                                    <PostOverviewCardWithName post={post} key={post.postId} type={'published'} />
                                )
                            })
                        }
                    </div>

                </div>

            </div>

        </div>
    );
};

export default HomePage;
