
import { Button, Card } from "antd"
import { IReply } from "../../store/postdetailinterface";
import styles from './style.module.scss';
import { Col, Row, Space, Avatar } from 'antd';
import { format } from 'date-fns';

import { ISubReply } from "../../store/postdetailinterface";
import GirlSvg1 from '../../assets/girl/girl2.svg'

import { useUserStore } from "../../store/user-store";
import { usePostDetailStore } from "../../store/postdetail-store";


interface props {
  reply: ISubReply,
  type?: string
}

const SubReplyCard: React.FC<props> = ({ reply, type }) => {

  const { user } = useUserStore();
  const { deleteReplyRequest } = usePostDetailStore();
  const deleteComment = () => {
    deleteReplyRequest(reply.replyId);
  }

  const disabled = !(2 === reply.userId); // user id === reply.userId
  const actions = disabled ? [] : [<Button type="primary" danger onClick={deleteComment}>Delete</Button>,]


  return (
    <Card hoverable={true} className={styles.card_wrapper}
      actions={actions}
    >
      <div className={styles.comment}>
        <div className={styles.title}>{reply.comment}</div>
      </div>
      <div className={styles.head}>
        <Row>
          <Col span={14}>
            <Space><Avatar size={30} src={GirlSvg1} />{reply.userId}</Space>

          </Col>
          <Col span={10}>
            {
              <div className={styles.date}> {format(reply.dateCreated, 'PPPp')}  </div>
            }
          </Col>
        </Row>
      </div>
    </Card>
  )
}

export default SubReplyCard