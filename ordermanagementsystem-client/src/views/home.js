import React from 'react'
import { Link } from 'react-router-dom'

import { Helmet } from 'react-helmet'

import './home.css'

const Home = (props) => {
  return (
    <div className="home-container">
      <Helmet>
        <title>Medica template</title>
        <meta property="og:title" content="Medica template" />
      </Helmet>
      <section className="home-hero">
        <header data-thq="thq-navbar" className="home-navbar">
          <div className="home-left">
            <Link to="/" className="home-navlink">
              <img
                alt="allianzlogo"
                src="/external/pastedimage-75g2.svg"
                className="home-pasted-image"
              />
            </Link>
          </div>
          <div data-thq="thq-navbar-btn-group" className="home-right">
            <a href="#register" className="home-book button button-main">
              <span className="home-text">
                <span>Register Now</span>
                <br></br>
              </span>
            </a>
          </div>
          <div data-thq="thq-burger-menu" className="home-burger-menu">
            <svg viewBox="0 0 1024 1024" className="home-icon">
              <path d="M128 554.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 298.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 810.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667z"></path>
            </svg>
          </div>
          <div data-thq="thq-mobile-menu" className="home-mobile-menu">
            <div
              data-thq="thq-mobile-menu-nav"
              data-role="Nav"
              className="home-nav"
            >
              <div className="home-container1">
                <img
                  alt="image"
                  src="/Branding/logo-1500h.png"
                  className="home-image"
                />
                <div data-thq="thq-close-menu" className="home-menu-close">
                  <svg viewBox="0 0 1024 1024" className="home-icon2">
                    <path d="M810 274l-238 238 238 238-60 60-238-238-238 238-60-60 238-238-238-238 60-60 238 238 238-238z"></path>
                  </svg>
                </div>
              </div>
              <nav
                data-thq="thq-mobile-menu-nav-links"
                data-role="Nav"
                className="home-nav1"
              >
                <span className="home-text03">Features</span>
                <span className="home-text04">How it works</span>
                <span className="home-text05">Prices</span>
                <span className="home-text06">Contact</span>
                <a href="#book" className="home-book1 button button-main">
                  <img
                    alt="image"
                    src="/Icons/calendar.svg"
                    className="home-image1"
                  />
                  <span className="home-text07">Book an appointment</span>
                </a>
              </nav>
            </div>
          </div>
        </header>
        <div className="home-main">
          <div className="home-content">
            <div className="home-heading">
              <h1 className="home-header">
                Revolutionize your workflow with new Allianz OMS.
              </h1>
              <p className="home-caption">
                Seamlessly streamline your order processes with our powerful,
                user-friendly API.
              </p>
            </div>
            <button className="home-login-button button button-main">
              <Link to="/login" className="home-navlink1">
                Login
              </Link>
            </button>
          </div>
          <div className="home-image2">
            <img alt="image" src="/aztrasset%201.svg" className="home-image3" />
          </div>
        </div>
        <div className="home-background"></div>
      </section>
      <section className="home-about">
        <div className="home-heading1">
          <h2 className="home-text08">
            <span className="home-text09">About the project</span>
            <br></br>
          </h2>
        </div>
        <div className="home-content1">
          <span className="home-text11">
            <span>
              This is a Full-Stack Order Management System web application,
              developed by @rburaksaritas during the internship at Allianz
              Türkiye. The project is mainly focused on role based access
              control. Developed with Test-Driven Development methodology.
              <span
                dangerouslySetInnerHTML={{
                  __html: ' ',
                }}
              />
            </span>
            <br></br>
            <br></br>
            <span>Utilized tools are: Java, Spring Boot, MySQL.</span>
          </span>
          <a
            href="https://www.github.com/rburaksaritas"
            data-open="practices"
            target="_blank"
            rel="noreferrer noopener"
            className="home-all button button-main"
          >
            <span>See Github</span>
          </a>
        </div>
      </section>
      <section id="register" className="home-register">
        <div className="home-heading2">
          <h2 className="home-text17">Register as a Customer now</h2>
          <p className="home-text18">
            Access all products, and place your order with one click
          </p>
        </div>
        <div className="home-form">
          <div className="home-inputs">
            <input
              type="text"
              required
              placeholder="Name"
              autoComplete="name"
              className="input book-input"
            />
            <input
              type="email"
              required
              placeholder="Email"
              autoComplete="email"
              className="input book-input"
            />
            <input
              type="password"
              required
              placeholder="Password"
              autoComplete="new-password"
              className="input book-input"
            />
            <input
              type="tel"
              required
              placeholder="Phone"
              autoComplete="tel"
              className="input book-input"
            />
            <label className="home-text19">Birth Date</label>
            <div className="home-birth-date">
              <input
                type="date"
                placeholder="Date"
                className="input book-input"
              />
              <img
                alt="image"
                src="/Icons/calendar-2.svg"
                className="home-image4"
              />
            </div>
            <input
              type="text"
              placeholder="Location"
              className="input book-input"
            />
            <input
              type="text"
              placeholder="Starting Balance"
              className="input book-input"
            />
            <div className="home-lower">
              <p className="home-text20">
                By registering to the platform, you agree that your information
                will be saved into local MySQL server. 
              </p>
              <div className="home-button">
                <button className="home-book2 button button-main">
                  <span>Register</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
      <div className="home-footer">
        <div className="home-left1">
          <div className="home-brand">
            <img
              alt="image"
              src="/aztrlogobeyazasset%202.svg"
              className="home-image5"
            />
          </div>
        </div>
        <div className="home-right1">
          <div className="home-legal">
            <a
              href="https://github.com/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="home-copyright"
            >
              <span>github.com/rburaksaritas </span>
              <span>© 2023  </span>
              <span>All Rights Reserved.</span>
            </a>
          </div>
          <div className="home-socials">
            <a
              href="https://linkedin.com/in/rburaksaritas"
              target="_blank"
              rel="noreferrer noopener"
              className="home-link"
            >
              <div className="home-social social">
                <img
                  alt="image"
                  src="/Icons/insider.svg"
                  className="home-image6"
                />
              </div>
            </a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Home
