import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

export const ArchivePostButton: React.FC = () => {
    const [form] = Form.useForm();
    const { archivePost } = usePostDetailStore();

    const onSubmitArchive = async () => {
        try {
            const response = await archivePost();
            form.resetFields();
        }
        catch (error) {
            message.error('An error occurred while archiving the post');
        }
    };

    return (
        <Form
            name="delete_reply_of_post"
            onFinish={onSubmitArchive}
            autoComplete="off"
        >
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Archive Post
                </Button>
            </Form.Item>
        </Form>
    );
};

export default ArchivePostButton;
