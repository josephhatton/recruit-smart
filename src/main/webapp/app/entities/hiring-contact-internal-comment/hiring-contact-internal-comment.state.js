(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('hiring-contact-internal-comment', {
        parent: 'entity',
        url: '/hiring-contact-internal-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContactInternalComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comments.html',
            controller: 'HiringContactInternalCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('hiring-contact-internal-comment-detail', {
        parent: 'hiring-contact-internal-comment',
        url: '/hiring-contact-internal-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContactInternalComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comment-detail.html',
            controller: 'HiringContactInternalCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'HiringContactInternalComment', function ($stateParams, HiringContactInternalComment) {
            return HiringContactInternalComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'hiring-contact-internal-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('hiring-contact-internal-comment-detail.edit', {
        parent: 'hiring-contact-internal-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comment-dialog.html',
            controller: 'HiringContactInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContactInternalComment', function (HiringContactInternalComment) {
                return HiringContactInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-internal-comment.new', {
        parent: 'hiring-contact-internal-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comment-dialog.html',
            controller: 'HiringContactInternalCommentDialogController',
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
            $state.go('hiring-contact-internal-comment', null, {reload: 'hiring-contact-internal-comment'});
          }, function () {
            $state.go('hiring-contact-internal-comment');
          });
        }]
      })
      .state('hiring-contact-internal-comment.edit', {
        parent: 'hiring-contact-internal-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comment-dialog.html',
            controller: 'HiringContactInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContactInternalComment', function (HiringContactInternalComment) {
                return HiringContactInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact-internal-comment', null, {reload: 'hiring-contact-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-internal-comment.delete', {
        parent: 'hiring-contact-internal-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact-internal-comment/hiring-contact-internal-comment-delete-dialog.html',
            controller: 'HiringContactInternalCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['HiringContactInternalComment', function (HiringContactInternalComment) {
                return HiringContactInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact-internal-comment', null, {reload: 'hiring-contact-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
