import React, { useState } from 'react';
import { Input, Modal, Button } from 'antd';
const { TextArea } = Input;
import './NewPost.scss';

interface NewPostProps {
    onAddPost: (post: { title: string; content: string }) => void;
}

export const NewPost: React.FC<NewPostProps> = ({ onAddPost }) => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [newPostTitle, setNewPostTitle] = useState('');
    const [newPostContent, setNewPostContent] = useState('');

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        if (newPostTitle.trim() && newPostContent.trim()) {
            onAddPost({ title: newPostTitle, content: newPostContent });
        }
        setIsModalVisible(false);
        setNewPostTitle('');
        setNewPostContent('');
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <>
            <Button type="primary" onClick={showModal} className="create-post-button">
                Create New Post
            </Button>
            <Modal title="Create New Post" open={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                <Input
                    placeholder="Title"
                    value={newPostTitle}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewPostTitle(e.target.value)}
                    style={{ marginBottom: 16 }} // Add spacing between title input and content textarea
                />
                <TextArea
                    placeholder="Content"
                    rows={4}
                    value={newPostContent}
                    onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setNewPostContent(e.target.value)}
                />
            </Modal>
        </>
    );
};
