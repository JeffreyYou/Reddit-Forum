import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

export const UnhidePostButton: React.FC = () => {
    const [form] = Form.useForm();
    const { unhidePost } = usePostDetailStore();

    const onSubmitUnhidePost = async () => {
        try {
            const response = await unhidePost();
            form.resetFields();
        }
        catch (error) {
            message.error('An error occurred while hiding the post');
        }
    };

    return (
        <Form
            name="hide_post"
            onFinish={onSubmitUnhidePost}
            autoComplete="off"
        >
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Unhide Post
                </Button>
            </Form.Item>

        </Form>
    );
};

export default UnhidePostButton;
