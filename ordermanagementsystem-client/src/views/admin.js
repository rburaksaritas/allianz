import React from 'react'

import { Helmet } from 'react-helmet'

import Header1 from '../components/header1'
import './admin.css'

const Admin = (props) => {
  return (
    <div className="admin-container">
      <Helmet>
        <title>Admin - Medica template</title>
        <meta property="og:title" content="Admin - Medica template" />
      </Helmet>
      <Header1></Header1>
      <section id="content" className="admin-content">
        <h1 className="admin-text">
          <span>Welcome</span>
          <br></br>
        </h1>
        <span>Below, you can see the list of...</span>
        <div className="admin-table-wrapper"></div>
      </section>
      <div className="admin-footer">
        <div className="admin-left">
          <div className="admin-brand">
            <img
              alt="image"
              src="/aztrlogobeyazasset%202.svg"
              className="admin-image"
            />
          </div>
        </div>
        <div className="admin-right">
          <div className="admin-legal">
            <a
              href="https://github.com/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="admin-copyright"
            >
              <span>github.com/rburaksaritas </span>
              <span>© 2023  </span>
              <span>All Rights Reserved.</span>
            </a>
          </div>
          <div className="admin-socials">
            <a
              href="https://linkedin.com/in/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="admin-link"
            >
              <div className="admin-social social">
                <img
                  alt="image"
                  src="/Icons/insider.svg"
                  className="admin-image1"
                />
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Admin
