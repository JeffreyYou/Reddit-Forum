import React, { useState } from 'react';
import { Input, Modal, Button, Upload, Image, message } from 'antd';
import type { UploadProps } from 'antd';
import styles from './style.module.scss';
import { UploadOutlined } from '@ant-design/icons';
import { useObjectStore } from '../../store/file-store';
import { IPutPostRequest } from '../../store/interface';

const { TextArea } = Input;

interface NewPostProps {
    // onAddPost: (post: { title: string; content: string }) => void;
}

export const NewPost: React.FC<NewPostProps> = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [newPostTitle, setNewPostTitle] = useState('');
    const [newPostContent, setNewPostContent] = useState('');

    const { imageUrls, putImageUrl, putPublishedPost } = useObjectStore()

    const props: UploadProps = {
        name: 'multipartFile',
        method: 'PUT',
        action: 'http://localhost:8081/file-service/reddit-forum-s3/public',
        headers: {
          Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw',
        },
        onChange(info) {
          if (info.file.status !== 'uploading') {
            console.log(info.file, info.fileList);
          }
          if (info.file.status === 'done') {
            const {response} = info.file
            console.log(response.url)
            putImageUrl(response.url)
            message.success(`file uploaded successfully`);
          } else if (info.file.status === 'error') {
            message.error(`${info.file.error} file upload failed.`);
          }
        },
      };

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        if (newPostTitle.trim() && newPostContent.trim()) {
            // onAddPost({ title: newPostTitle, content: newPostContent });
        }

        let body: IPutPostRequest = {
            postId: null,
            title: newPostTitle.trim(),
            content: newPostContent.trim(),
            images: imageUrls,
            attachments: []
        }
        
        putPublishedPost(body)

        setIsModalVisible(false);
        setNewPostTitle('');
        setNewPostContent('');
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <>
            <Button type="primary" onClick={showModal} className={styles.create_post_button}>
                Create New Post
            </Button>
            <Modal title="Create New Post" open={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                <Input
                    placeholder="Title"
                    value={newPostTitle}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewPostTitle(e.target.value)}
                    className={styles.create_post_input}
                />
                <TextArea
                    placeholder="Content"
                    rows={4}
                    value={newPostContent}
                    onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setNewPostContent(e.target.value)}
                />
                {imageUrls.map((url) => (
                    <Image
                        key={url}
                        src={url}
                        width={200}
                        height={150}
                    />
                ))}
                <Upload {...props}>
                    <Button icon={<UploadOutlined />}>upload image</Button>
                </Upload>
            </Modal>
        </>
    );
};
