<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pages">
  <div data-page="projects" class="page no-toolbar no-navbar">
    <div class="page-content">

      <div class="navbarpages">
        <!--<%&#45;&#45;<div class="nav_left_logo"><a href="#" data-panel="left" class="open-panel"><img src="images/logo.png" alt="" title="" /></a></div>&#45;&#45;%>-->
        <div class="nav_left_logo">
          <div id="nav_top">
            <a href="#" data-panel="left" class="open-panel" id="nav-top">
              <img src="images/icons/white/menu.png" alt="" title="" />
            </a>
          </div>
        </div>
        <div class="nav_right_button">
          <div id="nav_top2">
            <!--<%&#45;&#45;<a href="menu.html"><img src="images/icons/white/menu.png" alt="" title="" /></a>&#45;&#45;%>-->
            <a href="home" id="nav-top2">
              <img src="images/logo.png" alt="" title="" />
            </a>
            <!--<%&#45;&#45;<a href="#" data-panel="right" class="open-panel"><img src="images/icons/white/search.png" alt="" title="" /></a>&#45;&#45;%>-->
          </div>

        </div>
      </div>

      <div id="pages_maincontent">

        <h2 class="page_title">Student Rankings</h2>

        <div class="page_content">

          <h3>Top 5</h3>

          <ul class="responsive_table">
            <li class="table_row">
              <div class="table_section_small">#</div>
              <div class="table_section">Student</div>
              <div class="table_section">XP</div>
            </li>

          <%
              ArrayList<Announcement> announcements = ( ArrayList<Announcement> ) request.getAttribute( "announcements" );

              for ( Announcement announcement : announcements )
              {
              out.println("<li>");
              out.println("<div class=\"post_entry\">");
              out.println("<div class=\"post_date\">");
              out.println("<span class=\"day\">" + announcement.getDay() + "</span>");
              out.println("<span class=\"month\">" + announcement.getMonth() + "</span>");
              out.println("</div>");
              out.println("<div class=\"post_title\">");
              out.println("<h2><a href=\"single-announcement?id=" + announcement.getId() + "\">" + announcement.getTitle() + "</a></h2>");
              out.println("</div>");
              out.println("</div>");
              out.println("</li>");

              }
          %>
            <li class="table_row">
              <div class="table_section_small">01</div>
              <div class="table_section">Web design</div>
              <div class="table_section">$100 / hour</div>
            </li>
            <li class="table_row">
              <div class="table_section_small">02</div>
              <div class="table_section">Drawing</div>
              <div class="table_section">$60 / hour</div>
            </li>
            <li class="table_row">
              <div class="table_section_small">03</div>
              <div class="table_section">Illustration</div>
              <div class="table_section">$80 / hour</div>
            </li>
            <li class="table_row">
              <div class="table_section_small">04</div>
              <div class="table_section">Layout design</div>
              <div class="table_section">$80 / hour</div>
            </li>
            <li class="table_row">
              <div class="table_section_small">05</div>
              <div class="table_section">Programming</div>
              <div class="table_section">$120 / hour</div>
            </li>
          </ul>

          <p>With this table design you can create a list of web design services.</p>

          <h3>Price table</h3>

          <ul class="responsive_table">
            <li class="table_row">
              <div class="table_section_14">&nbsp;</div>
              <div class="table_section_14">Medium</div>
              <div class="table_section_14">Business</div>
              <div class="table_section_14">Deluxe</div>
            </li>
            <li class="table_row">
              <div class="table_section_14">Storage</div>
              <div class="table_section_14">1 GB</div>
              <div class="table_section_14">2 GB</div>
              <div class="table_section_14">4 GB</div>
            </li>
            <li class="table_row">
              <div class="table_section_14">Bandwidth</div>
              <div class="table_section_14">50 GB</div>
              <div class="table_section_14">100 GB</div>
              <div class="table_section_14">Unlimited</div>
            </li>
            <li class="table_row">
              <div class="table_section_14">Databases</div>
              <div class="table_section_14">50</div>
              <div class="table_section_14">Unlimited</div>
              <div class="table_section_14">Unlimited</div>
            </li>
            <li class="table_row">
              <div class="table_section_14">PHP</div>
              <div class="table_section_14"><img src="images/checked.png" alt="" title="" /></div>
              <div class="table_section_14"><img src="images/checked.png" alt="" title="" /></div>
              <div class="table_section_14"><img src="images/checked.png" alt="" title="" /></div>
            </li>
            <li class="table_row">
              <div class="table_section_14">Price</div>
              <div class="table_section_14"><strong>$ 50</strong></div>
              <div class="table_section_14"><strong>$ 100</strong></div>
              <div class="table_section_14"><strong>$ 200</strong></div>
            </li>
            <li class="table_row">
              <div class="table_section_14">&nbsp;</div>
              <div class="table_section_14"><a href="checkin.html" class="buy_now">BUY NOW</a></div>
              <div class="table_section_14"><a href="checkin.html" class="buy_now">BUY NOW</a></div>
              <div class="table_section_14"><a href="checkin.html" class="buy_now">BUY NOW</a></div>
            </li>
          </ul>

          <p>With this table design you can create a price table for your services.</p>


        </div>

      </div>


    </div>
  </div>
</div>
