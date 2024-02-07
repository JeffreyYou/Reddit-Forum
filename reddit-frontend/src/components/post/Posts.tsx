import React from 'react';
import { Card } from 'antd';
import styles from './style.module.scss';

interface PostsProps {
    posts: IPost[];
}

export const Posts: React.FC<PostsProps> = ({ posts }) => {
    return (
        <div className={styles.posts_container}>
            {posts.map((post) => (
                <Card
                    key={post.id}
                    className="reddit-card"
                    hoverable
                >
                    <h3 className="reddit-card-title">{post.title}</h3>
                    <p className="reddit-card-meta">{`${post.username} - ${post.date}`}</p>
                </Card>
            ))}
        </div>
    );
};

export interface IPost {
    id: string;
    username: string;
    date: Date;
    title: string;
}

// Replace the samplePosts with actual data or API call to fetch posts
// sample data
export const samplePosts: IPost[] = [
    {id: "id1", username: 'User1', date: new Date(2024, 2, 1), title: 'First Post'},
    {id: "id2", username: 'User2', date: new Date(2024, 2, 2), title: 'Second Post'},
];
