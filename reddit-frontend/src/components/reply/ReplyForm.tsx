import React from 'react';
import { Form, Input, Button, message } from 'antd';
import { usePostDetailStore } from '../../store/postdetail-store';

interface IReplyInfo {
    replyId: string
}

export const ReplyForm: React.FC<IReplyInfo> = ({replyId}) => {
    const [form] = Form.useForm();
    const { submitReplyRequest } = usePostDetailStore();

    const onSubmitReply = async (replyRequest: any) => {
        try {
            const response = await submitReplyRequest(replyRequest, replyId);
            if (response.responseStatus.success) {
                message.success(response.responseStatus.success);
                form.resetFields();
            } else {
                message.error('Failed to reply to the post');
            }
        }
        catch (error) {
            message.error('An error occurred while replying to the post');
        }
    };

    return (
        <Form
            name="reply_to_post"
            onFinish={onSubmitReply}
            autoComplete="off"
        >
            <Form.Item
                label="Comment"
                name="comment"
            >
                <Input />
            </Form.Item>
            
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>

        </Form>
    );
};

export default ReplyForm;
