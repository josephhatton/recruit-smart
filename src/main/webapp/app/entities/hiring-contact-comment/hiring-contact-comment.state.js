(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('hiring-contact-comment', {
        parent: 'entity',
        url: '/hiring-contact-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContactComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comments.html',
            controller: 'HiringContactCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('hiring-contact-comment-detail', {
        parent: 'hiring-contact-comment',
        url: '/hiring-contact-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContactComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comment-detail.html',
            controller: 'HiringContactCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'HiringContactComment', function ($stateParams, HiringContactComment) {
            return HiringContactComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'hiring-contact-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('hiring-contact-comment-detail.edit', {
        parent: 'hiring-contact-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comment-dialog.html',
            controller: 'HiringContactCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContactComment', function (HiringContactComment) {
                return HiringContactComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-comment.new', {
        parent: 'hiring-contact-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comment-dialog.html',
            controller: 'HiringContactCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('hiring-contact-comment', null, {reload: 'hiring-contact-comment'});
          }, function () {
            $state.go('hiring-contact-comment');
          });
        }]
      })
      .state('hiring-contact-comment.edit', {
        parent: 'hiring-contact-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comment-dialog.html',
            controller: 'HiringContactCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContactComment', function (HiringContactComment) {
                return HiringContactComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact-comment', null, {reload: 'hiring-contact-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-comment.delete', {
        parent: 'hiring-contact-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-comment/hiring-contact-comment-delete-dialog.html',
            controller: 'HiringContactCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['HiringContactComment', function (HiringContactComment) {
                return HiringContactComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact-comment', null, {reload: 'hiring-contact-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
