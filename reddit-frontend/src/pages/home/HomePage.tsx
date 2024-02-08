// Frontend code
import React, { useEffect, useState } from 'react';
import { Card, Button } from 'antd';
import styles from './style.module.scss';
import {usePostStore} from "../../store/post-store.ts";
import {IPostDetail} from "../../store/interface.ts";
import {NewPost} from "../../components/new-post/NewPost.tsx";
// import {useHistory} from "react-router-dom";

const HomePage: React.FC = () => {
    const [posts, setPosts] = useState<IPostDetail[]>([]);
    // const history = useHistory();
    const { getPublishedPosts } = usePostStore();

    useEffect(() => {
        fetchPublishedPosts();
    }, []);

    const fetchPublishedPosts = async () => {
        try {
            const publishedPosts = await getPublishedPosts();
            setPosts(publishedPosts);
        } catch (error) {
            console.error('Failed to fetch published posts:', error);
        }
    };

    const handleNewPost = () => {
        // Navigate to the page for creating a new post
        // history.push('/new-post');
    };

    return (
        <div className={styles.homepage_container}>
            <div className={styles.content}>
                <h1>Hello! This is your User homepage.</h1>
                <NewPost />
                <Button type="primary" onClick={handleNewPost}>New Post</Button>
                {/*{posts.map((post) => (*/}
                {/*    <Card key={post.postId} className={styles.post_card} hoverable>*/}
                {/*        <p>{`Date: ${post.dateCreated.toLocaleString()}`}</p>*/}
                {/*        <h3>{post.title}</h3>*/}
                {/*    </Card>*/}
                {/*))}*/}
            </div>
        </div>
    );
};

export default HomePage;
