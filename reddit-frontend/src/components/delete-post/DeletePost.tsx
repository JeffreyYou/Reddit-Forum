import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

export const DeletePostButton: React.FC = () => {
    const [form] = Form.useForm();
    const { deletePost } = usePostDetailStore();

    const onSubmitDeletePost = async () => {
        try {
            const response = await deletePost();
            form.resetFields();
        }
        catch (error) {
            message.error('An error occurred while hiding the post');
        }
    };

    return (
        <Form
            name="hide_post"
            onFinish={onSubmitDeletePost}
            autoComplete="off"
        >
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Delete Post
                </Button>
            </Form.Item>

        </Form>
    );
};

export default DeletePostButton;
