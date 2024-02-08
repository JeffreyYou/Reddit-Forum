import React, { useState } from 'react';
import {Input, Modal, Button, Upload, Image, message, FloatButton} from 'antd';
import type { UploadProps } from 'antd';
import styles from './style.module.scss';
import { UploadOutlined } from '@ant-design/icons';
import { useObjectStore } from '../../store/file-store';
import { IPutPostRequest } from '../../store/interface';
import {usePostStore} from "../../store/post-store.ts";

const { TextArea } = Input;

interface NewPostProps {
    // onAddPost: (post: { title: string; content: string }) => void;
}

export const NewPost: React.FC<NewPostProps> = () => {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [newPostTitle, setNewPostTitle] = useState('');
    const [newPostContent, setNewPostContent] = useState('');
    const [isPublish, setIsPublish] = useState(true);
    // the key is usedÏΩ to make sure each time the modal is opened, the Upload component is treated as a new instance
    const [uploadKey, setUploadKey] = useState(Date.now());

    const { imageUrls, attachmentUrls, putImageUrl, deleteImageUrl,  putAttachmentUrl, deleteAttachmentUrl} = useObjectStore()
    const { putPublishedPost, savePost } = usePostStore();

    const save = () => {
        if (newPostTitle.trim() && newPostContent.trim()) {
            // onAddPost({ title: newPostTitle, content: newPostContent });
        }

        const body: IPutPostRequest = {
            postId: null,
            title: newPostTitle.trim(),
            content: newPostContent.trim(),
            images: imageUrls,
            attachments: []
        }

        savePost(body);
        imageUrls.forEach(url => deleteImageUrl(url));

        setIsModalVisible(false);
        setNewPostTitle('');
        setNewPostContent('');

        setIsPublish(false)
    }

    const props: UploadProps = {
        name: 'multipartFile',
        method: 'PUT',
        action: 'http://localhost:8081/file-service/reddit-forum-s3/public',
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem("jwtToken"),
        },
        onChange(info) {
            if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
            }
            if (info.file.status === 'done') {
                const {response} = info.file
                console.log("info.file.type")
                console.log(info.file.type)
                if(info.file.type == "image/jpeg") putImageUrl(response.url)
                else putAttachmentUrl(response.url)
                message.success(`file uploaded successfully`);
            } else if (info.file.status === 'error') {
                message.error(`${info.file.error} file upload failed.`);
            }
        },
        onRemove: (file) => {
            const { response } = file;
            const removedUrl = response.url;
            deleteImageUrl(removedUrl);
            return true;
        },
    };

    const showModal = () => {
        setIsModalVisible(true);
        setUploadKey(Date.now());
    };

    const handleOk = () => {
        if(!isPublish) return;
        if (newPostTitle.trim() && newPostContent.trim()) {
            // onAddPost({ title: newPostTitle, content: newPostContent });
        }

        const body: IPutPostRequest = {
            postId: null,
            title: newPostTitle.trim(),
            content: newPostContent.trim(),
            images: imageUrls,
            attachments: attachmentUrls
        }

        putPublishedPost(body)
        imageUrls.forEach(url => deleteImageUrl(url));

        setIsModalVisible(false);
        setNewPostTitle('');
        setNewPostContent('');
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <>
            <FloatButton type="primary" onClick={showModal} className={styles.create_post_button}>
                Create New Post
            </FloatButton>
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
                <Upload {...props}  key={uploadKey}>
                    <Button icon={<UploadOutlined/>}>upload image or attachment</Button>
                </Upload>
                <br></br>
                <Button onClick={save}>save</Button>
            </Modal>
        </>
    );
};