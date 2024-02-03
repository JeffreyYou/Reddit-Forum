import React from 'react';
import { List } from 'antd';
import './Posts.scss';

interface PostsProps {
    posts: IPost[];
}

export const Posts: React.FC<PostsProps> = ({ posts }) => {
    return (
        <div className="posts-container">
            <List
                itemLayout="horizontal"
                dataSource={posts}
                renderItem={(item) => (
                    <List.Item>
                        <div className="post-meta">
                            <div className="post-user-date">{`${item.username} - ${item.date}`}</div>
                            <div className="post-title">
                                 {/*todo: use Link*/}
                                <a href={`/posts/${item.id}`}>{item.title}</a>
                            </div>
                        </div>
                    </List.Item>
                )}
            />
        </div>
    );
};

export interface IPost {
    id: number;
    username: string;
    date: string;
    title: string;
}

// sample data
export const samplePosts: IPost[] = [
    {id: 1, username: 'User1', date: '2024-02-01', title: 'First Post'},
    {id: 2, username: 'User2', date: '2024-02-02', title: 'Second Post'},
];