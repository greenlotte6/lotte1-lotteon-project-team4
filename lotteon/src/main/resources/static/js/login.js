document.addEventListener('DOMContentLoaded', function () {
    const bar = document.querySelector('.bar');
    const barcate = document.querySelector('.bar-cate');
    const fullview = document.querySelector('.full-view');
    const fullmenu = document.querySelector('.full-menu');
    const all = document.getElementsByClassName('all')[0];
    const rank = document.querySelector('.rank');
    const ranknav = document.getElementById('rank-nav');

    rank.addEventListener('click', () => {
        ranknav.classList.toggle('rank-show');
    });

    rank.addEventListener('mouseleave', () => {
        ranknav.classList.remove('rank-show');
    });
    /*
    const rankitem = document.getElementsByClassName('rank-item')[0];
    let currentIndex = 0;
    */

    bar.addEventListener('click', () => {
        barcate.classList.toggle('show');
    });

    bar.addEventListener('mouseleave', () => {
        barcate.classList.remove('show');
    });

    fullview.addEventListener('click', () => {
        fullmenu.classList.toggle('menu');
    });

    all.addEventListener('mouseleave', () => {
        fullmenu.classList.remove('menu');
    });

    /*
    function showNextItem() {
        currentIndex = (currentIndex + 1) % rankitem.length;
        ranklist.style.transform = `translateY(-${currentIndex * 100}%)`;
    }

    setInterval(showNextItem, 3000);
    */
    const recentLogin = localStorage.getItem("provider");
    const providers = ["kakao", "google", "naver"];


    const SocialLoginButton = ({ provider, recentLogin }) => {
        const authorizationUrl = `${process.env.REACT_APP_API_URL}/oauth2/authorization`;

        // 애니메이션
        const springProps = useSpring({
            display: 'inline',
            loop: { reverse: true },
            from: { y: 0 },
            to: { y: 2 },
            config: {
                tension: 300,
                friction: 30,
            }
        })

        return (
            <CCol xs={4} className={"text-center"}>
                <a href={`${authorizationUrl}/${provider}`} className="social-button" id={`${provider}-connect`}></a>
                {
                    provider === recentLogin
                        ?
                        <animated.div style={springProps}>
                            <p style={{ fontSize: "12px", fontWeight: "bold" }}>최근 로그인</p>
                        </animated.div>
                        : <></>
                }
            </CCol>
        )
    }
});

    document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.login-form');

    form.addEventListener('submit', function (e) {
    const id = form.id.value.trim();
    const password = form.password.value.trim();

    if (id === '' || password === '') {
    e.preventDefault();
    alert('아이디와 비밀번호를 모두 입력해 주세요.');
    return false;
}

    });
});

