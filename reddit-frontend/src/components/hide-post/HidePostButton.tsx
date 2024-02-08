import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

export const HidePostButton: React.FC = () => {
    const [form] = Form.useForm();
    const { hidePost } = usePostDetailStore();

    const onSubmitHidePost = async () => {
        try {
            const response = await hidePost();
            form.resetFields();
        }
        catch (error) {
            message.error('An error occurred while hiding the post');
        }
    };

    return (
        <Form
            name="hide_post"
            onFinish={onSubmitHidePost}
            autoComplete="off"
        >
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Hide Post
                </Button>
            </Form.Item>

        </Form>
    );
};

export default HidePostButton;
