
import { Card } from "antd"
import { IPostDetail } from "../../store/interface"
import styles from './style.module.scss';
import { Col, Row } from 'antd';
import { parseISO, format } from 'date-fns';

interface props {
  post: IPostDetail
}
const placeholder = {
  title: "This is the Title of the Posts, This is the Title of the Posts, This is the Title of the Posts, This is the Title of the Posts",
  date: "2021-09-01",
  content: "Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read. Post content goes here. This can be a summary or an excerpt from the full post for a quick read."
}
const PostOverviewCard: React.FC<props> = ({ post }) => {
  return (
    <Card hoverable={true} className={styles.card_wrapper}>
      <div className={styles.head}>
        <Row>
          <Col span={14}>
            <div className={styles.title}>{post.title}</div>
          </Col>
          <Col span={10}>
            <div className={styles.date}> {format(post.dateModified, 'PPPp')}  </div>
          </Col>
        </Row>
      </div>
      <div className={styles.content}>
        {post.content}
      </div>
    </Card>
  )
}

export default PostOverviewCard