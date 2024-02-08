import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

interface IReplyInfo {
    replyId: string
}

export const DeleteReplyButton: React.FC<IReplyInfo> = ({replyId}) => {
    const [form] = Form.useForm();
    const { deleteReplyRequest } = usePostDetailStore();

    const onSubmitDeleteReply = async () => {
        try {
            const response = await deleteReplyRequest(replyId);
            if (response.responseStatus.success) {
                message.success(response.responseStatus.success);
                form.resetFields();
            } else {
                message.error('Failed to delete reply');
            }
        }
        catch (error) {
            message.error('An error occurred while deleting the reply');
        }
    };

    return (
        <Form
            name="delete_reply_of_post"
            onFinish={onSubmitDeleteReply}
            autoComplete="off"
        >
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Delete
                </Button>
            </Form.Item>

        </Form>
    );
};

export default DeleteReplyButton;
