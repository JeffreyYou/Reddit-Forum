
import { Card } from "antd"
import { IReply } from "../../store/postdetailinterface";
import styles from './style.module.scss';
import { Col, Row, Space, Avatar, Input, Button } from 'antd';
import { format } from 'date-fns';
import SubReplyCard from "../subreply-card";
import GirlSvg1 from '../../assets/girl/girl2.svg'
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { useState } from "react";
import { usePostDetailStore } from "../../store/postdetail-store";
import { useUserStore } from "../../store/user-store";
interface props {
  reply: IReply,
  type?: string
}

const ReplyCard: React.FC<props> = ({ reply, type }) => {

  const [comment, setComment] = useState();
  const { user } = useUserStore();
  const { deleteReplyRequest } = usePostDetailStore();
  const deleteComment = () => {
    deleteReplyRequest(reply.replyId);
  }

  const handleInputChange = (e) => {
    setComment(e.target.value);
  }
  const submitComment = () => { 
    console.log(comment);
  }
  const disabled = !(2 === reply.userId); // user id === reply.userId

  return (
    <Card hoverable={true} className={styles.card_wrapper}
      actions={[
        <Button type="primary" danger disabled={disabled ? true : false} onClick={deleteComment}>Delete</Button>,
        <Input onChange={(e) => handleInputChange(e)} />,
        <Button type="primary">Comment</Button>,
      ]}
    >
      <div className={styles.comment}>
        <div className={styles.title}>{reply.comment}</div>
      </div>
      <div className={styles.head}>
        <Row>
          <Col span={14}>
            <Space><Avatar size={50} src={GirlSvg1} />{reply.userId}</Space>

          </Col>
          <Col span={10}>
            {
              <div className={styles.date}> {format(reply.dateCreated, 'PPPp')}  </div>
            }
          </Col>
        </Row>
      </div>
      {reply.subReplies.map((subreply, index) => {
        return (
          <SubReplyCard key={index} reply={subreply} />
        )
      })}
    </Card>
  )
}

export default ReplyCard