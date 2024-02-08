
import { Card } from "antd"
import { IPostDetail } from "../../store/interface"
import styles from './style.module.scss';
import { Col, Row, Space, Avatar } from 'antd';
import { format } from 'date-fns';

import GirlSvg1 from '../../assets/girl/girl2.svg'

interface props {
  post: IPostDetail,
  type: string
}
const placeholder = {
  title: "This is the Title of the Posts, This is the Title of the Posts, This is the Title of the Posts, This is the Title of the Posts",
  date: "2021-09-01",
  content: "Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read."
}
const PostOverviewCardWithName: React.FC<props> = ({ post, type }) => {

  return (
    <Card hoverable={true} className={styles.card_wrapper}>
      <div className={styles.head}>
        <Row>
          <Col span={14}>
            <div className={styles.title}>{post.title}</div>
          </Col>
          <Col span={10}>
            {
              <div className={styles.date}> {format(post.dateCreated, 'PPPp')}  </div> 
            }
          </Col>
        </Row>
      </div>
      <div className={styles.usercontent}>
        <Space><Avatar size={50} src={GirlSvg1} /> Jeffrey You</Space>
      </div>
    </Card>
  )
}

export default PostOverviewCardWithName