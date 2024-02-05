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
    id: number;
    username: string;
    date: string;
    title: string;
}

// Replace the samplePosts with actual data or API call to fetch posts
// sample data
export const samplePosts: IPost[] = [
    {id: 1, username: 'User1', date: '2024-02-01', title: 'First Post'},
    {id: 2, username: 'User2', date: '2024-02-02', title: 'Second Post'},
];