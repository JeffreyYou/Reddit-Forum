import React from 'react';
import { Card } from 'antd';
import styles from './style.module.scss';
import {IPostDetail} from "../../store/interface.ts";

interface PostsProps {
    posts: IPostDetail[];
}

export const Posts: React.FC<PostsProps> = ({ posts }) => {
    return (
        <div className={styles.posts_container}>
            {posts.map((post) => (
                <Card
                    key={post.postId}
                    className="reddit-card"
                    hoverable
                >
                    <h3 className="reddit-card-title">{post.title}</h3>
                    <p className="reddit-card-meta">{`USERNAME - ${post.dateCreated}`}</p>
                </Card>
            ))}
        </div>
    );
};
