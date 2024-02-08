import { Card, Descriptions, Modal, Image, Button, Col, Row } from 'antd';
import { SettingOutlined } from '@ant-design/icons';
import styles from './style.module.scss';

import { useEffect } from 'react';
import UserProfileCard from '../../components/user-profile-card';
import { useUserStore } from '../../store/user-store';
import { usePostDetailStore } from '../../store/postdetail-store';

import { EditPostForm } from '../../components/edit-post/EditPostForm';
import { PublishPostForm } from '../../components/publish-post/PublishPostForm';
import { ReplyForm } from '../../components/reply/ReplyForm';
import { DeleteReplyButton } from '../../components/delete-reply/DeleteReplyButton';
import { ArchivePostButton } from '../../components/archive-post/ArchivePostButton'
import { HidePostButton } from '../../components/hide-post/HidePostButton'
import { UnhidePostButton } from '../../components/unhide-post/UnhidePost'
import { DeletePostButton } from '../../components/delete-post/DeletePost'

const PostDetail = () => {
    const { postdetail, fetchPostDetail } = usePostDetailStore();
    const postReplies = postdetail.postReplies;

    useEffect(() => {
        fetchPostDetail();
      }, []);

    return (
        <div className={styles.profile_wrapper}>
            <div className={styles.profile}>
                {/* <Row style={{height: '100%'}}>
                <Col span={14} style={{height: '100%'}}>
                <Col span={24} style={{height: '30%'}}></Col>
                <Col span={24} style={{height: 'calc(70% - 148px)'}}><Card title="Post Information" extra={<SettingOutlined />} className={styles.draft_card}></Card></Col>
                </Col>
                <Col span={10}> */}

                { !(postdetail.userId === 2 && (postdetail.status === "Published" || postdetail.status === "Hidden")) ? <></> :
                    <Card
                        title="Post Edit Information"
                        extra={<SettingOutlined />}
                        className= {styles.posts_card}
                    >
                        <Descriptions title="Edit Post">
                            <Modal centered title="Edit Post">
                                <EditPostForm />
                            </Modal> 
                        </Descriptions>
                    </Card>
                }

                { !(postdetail.userId === 2 && postdetail.status === "Unpublished") ? <></> :
                    <Card
                        title="Post Publish Information"
                        extra={<SettingOutlined />}
                        className= {styles.posts_card}
                    >
                        <Descriptions title="Publish Post">
                            <Modal centered title="Publish Post">
                                <PublishPostForm />
                            </Modal> 
                        </Descriptions>
                    </Card>
                }

                {
                    postdetail.userId === 2 && !postdetail.isArchived ? 
                    <Descriptions.Item label="Archive Post">
                        <ArchivePostButton />
                    </Descriptions.Item> : <></>
                }

                {
                    postdetail.userId === 2 && postdetail.status === "Published" ? 
                    <Descriptions.Item label="Hide Post">
                        <HidePostButton />
                    </Descriptions.Item> : <></>
                }

                {
                    postdetail.userId === 2 && postdetail.status === "Hidden" ? 
                    <Descriptions.Item label="Unhide Post">
                        <UnhidePostButton />
                    </Descriptions.Item> : <></>
                }

                {
                    postdetail.userId === 2 && postdetail.status === "Published" ? 
                    <Descriptions.Item label="Delete Post">
                        <DeletePostButton />
                    </Descriptions.Item> : <></>
                }

                <Card
                    title="Post Information"
                    extra={<SettingOutlined />}
                    className= {styles.user_card}
                >
                    <Descriptions title="Post Info">
                        <Descriptions.Item label="Post ID">{postdetail.postId}</Descriptions.Item>
                        <Descriptions.Item label="Post Title">{postdetail.title}</Descriptions.Item>
                        <Descriptions.Item label="Post Content">{postdetail.content}</Descriptions.Item>
                        <Descriptions.Item label="User ID">{postdetail.userId}</Descriptions.Item>
                        <Descriptions.Item label="Date Created">{postdetail.dateCreated.substring(0, 10)}</Descriptions.Item>
                        {postdetail.dateCreated === postdetail.dateModified ? <></> : <Descriptions.Item label="Date Modified">{postdetail.dateModified.substring(0, 10)}</Descriptions.Item>}
                        <Descriptions.Item label="Post Images">
                            {postdetail.images.map((image, index) => (
                                // <div key={index}> {image} </div>
                                <img
                                    key={index}
                                    src={image}
                                    width={200}
                                    height={150}
                                />
                            ))}
                        </Descriptions.Item>
                        <Descriptions.Item label="Post Attachments">
                            {postdetail.attachments.map((attachment, index) => (
                                <div key={index}> {attachment} </div>
                            ))}
                        </Descriptions.Item>
                        <Descriptions.Item label="Post isArchived">{String(postdetail.isArchived)}</Descriptions.Item>
                        <Descriptions.Item label="Post Status">{postdetail.status}</Descriptions.Item>
                        <Descriptions.Item label="Post Replies">
                            {postReplies.map((reply, index) => (
                                <div key={index}>
                                    {reply.comment} {reply.userId} 
                                    {reply.userId === postdetail.userId ? <DeleteReplyButton replyId={reply.replyId}/> : <></>}
                                    {postdetail.isArchived ? <></> : <ReplyForm replyId={reply.replyId} />}
                                    {reply.subReplies.map((subReply, i) => (
                                        <div key={i}>
                                            {subReply.comment} {subReply.userId} {subReply.userId === postdetail.userId ? <DeleteReplyButton replyId={subReply.replyId} /> : <></>}
                                        </div>
                                    ))}
                                </div>
                            ))}
                            {postdetail.isArchived ? <></> : <ReplyForm replyId={"-1"} />}
                        </Descriptions.Item>
                    </Descriptions>
                </Card>
                {/* </Col>
                </Row> */}
            </div>
        </div>
    )
}

// {fileBlocks["blocks_content"].map((element, index) => (
//     <List key={index}>
//     {
//       element.map((line, i) => (
//          <List.Item key={i}>
//            {line}
//          </List.Item>
//       ))
//      }
//     </List>
// ))}

export default PostDetail